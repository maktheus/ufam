% main_project8.m
% PDI - Projeto 8: GlobalThresholding
% Autor: Matheus Serrao Uchoa
%
% Requer: globalThresh.m no mesmo diretorio
% Imagem: image.tif (rice-shaded.tif)

clear; clc; close all;

f = imread('image.tif');

%% =========================================================
%  Parte (b): globalThresh com configuracoes padrao (detT = 0.01)
%% =========================================================
fprintf('=== Parte (b): detT = 0.01 (padrao) ===\n');

[g_default, T_default, iters_default] = globalThresh(f);

fprintf('  Limiar T = %.6f  (escala [0,1])\n', T_default);
fprintf('  Iteracoes: %d\n', iters_default);
fprintf('  Pixels objeto (brancos): %d / %d (%.1f%%)\n', ...
        sum(g_default(:)), numel(g_default), ...
        100*sum(g_default(:))/numel(g_default));

figure('Name', 'Parte (b) - detT padrao');
subplot(1,3,1); imshow(f, []); title('Original (rice-shaded)');
subplot(1,3,2);
f_scaled = double(f); f_scaled = (f_scaled - min(f_scaled(:))) / (max(f_scaled(:)) - min(f_scaled(:)));
histogram(f_scaled(:), 64, 'FaceColor', [0.2 0.4 0.8]);
xline(T_default, 'r-', sprintf('T=%.3f', T_default), 'LineWidth', 2);
title('Histograma + limiar'); xlabel('Intensidade normalizada');
subplot(1,3,3); imshow(g_default); title(sprintf('Limiarizado (T=%.3f)', T_default));
sgtitle(sprintf('Parte (b): detT=0.01 | T=%.4f | %d iter.', T_default, iters_default));

%% =========================================================
%  Parte (c): variacao de detT
%% =========================================================
fprintf('\n=== Parte (c): variacao de detT ===\n');

detT_vals = [0.1, 0.01, 0.001, 0.0001];

figure('Name', 'Parte (c) - variacao de detT');
for k = 1:4
  [g_k, T_k, it_k] = globalThresh(f, detT_vals(k));
  fprintf('  detT = %.4f -> T = %.6f | iters = %d\n', detT_vals(k), T_k, it_k);
  subplot(2,4,k);
  imshow(g_k);
  title(sprintf('detT=%.4f\nT=%.4f | %dit', detT_vals(k), T_k, it_k));
  subplot(2,4,k+4);
  f_sc = double(f); f_sc = (f_sc - min(f_sc(:))) / (max(f_sc(:)) - min(f_sc(:)));
  histogram(f_sc(:), 64, 'FaceColor', [0.2 0.6 0.4]);
  xline(T_k, 'r-', 'LineWidth', 1.5);
  xlabel('Intensidade'); title(sprintf('Hist. detT=%.4f', detT_vals(k)));
end
sgtitle('Parte (c): influencia do criterio de parada detT');

fprintf('\n=== Concluido ===\n');
